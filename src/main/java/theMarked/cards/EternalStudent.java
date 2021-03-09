package theMarked.cards;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theMarked.DefaultMod;
import theMarked.VFX.SwordBuffEffect;
import theMarked.actions.IncreaseMagicAction;
import theMarked.actions.SummonAction;
import theMarked.actions.SwordBuffAction;
import theMarked.characters.TheMarked;

import static theMarked.DefaultMod.makeCardPath;

public class EternalStudent extends AbstractMarkedCard implements CustomSavable<Integer> {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(EternalStudent.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill_EternalStudent.png");
    public static final String IMG_beta = makeCardPath("Skill_EternalStudent_beta.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheMarked.Enums.MARKED_GENTA;

    private static final int COST = 2;
    private static final int MAGIC = 1; //increase
    private static final int MAGIC2 = 9; //base


    // /STAT DECLARATION/


    public EternalStudent() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        DefaultMod.loadJokeCardImage(this,IMG_beta);

        this.cardsToPreview = new AlyssasBlade();
        this.misc = MAGIC2;
        this.defaultBaseSecondMagicNumber = MAGIC;
        this.defaultSecondMagicNumber = defaultBaseSecondMagicNumber;
        this.baseMagicNumber = this.misc;
        this.magicNumber = defaultBaseSecondMagicNumber;
        this.exhaust = true;
        this.isEthereal = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard sword = this.cardsToPreview;
        this.addToBot(new IncreaseMagicAction(this.uuid, this.misc, this.defaultSecondMagicNumber));
        AbstractDungeon.actionManager.addToBottom(new SummonAction(sword));
        AbstractDungeon.actionManager.addToBottom(new SwordBuffAction(magicNumber));
        //this.addToBot(new IncreaseMagicAction(this.defaultSecondMagicNumber, this));

        float color_intensity = 150.0F + (105.0F * 1-(1/magicNumber));
        this.addToBot(new VFXAction(new SwordBuffEffect(p.hb.cX, p.hb.cY, CardHelper.getColor(color_intensity,color_intensity,color_intensity))));
        AbstractDungeon.actionManager.addToBottom(new SFXAction("BUFF_2"));
        initializeDescription();
    }

    @Override
    public void hover()
    {
        if (AbstractDungeon.getCurrMapNode() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            this.cardsToPreview = this.searchBlade();
        } else this.cardsToPreview = new AlyssasBlade();
        super.hover();
    }

    private AbstractCard searchBlade()
    {
        AbstractCard x = new AlyssasBlade();
        AbstractCard ca = null;
        for (AbstractCard c : AbstractDungeon.player.hand.group)
        {
            if (c instanceof AlyssasBlade) ca = c;
        }
        for (AbstractCard c : AbstractDungeon.player.discardPile.group)
        {
            if (c instanceof AlyssasBlade) ca = c;
        }
        for (AbstractCard c : AbstractDungeon.player.drawPile.group)
        {
            if (c instanceof AlyssasBlade) ca = c;
        }
        if (ca != null)
        {
            x.baseDamage = ca.baseDamage;
            x.baseMagicNumber = ca.baseMagicNumber;
            ((AbstractDynamicCard)x).defaultBaseSecondMagicNumber = ((AbstractDynamicCard)ca).defaultBaseSecondMagicNumber;
            x.baseBlock = ca.baseBlock;

            x.damage = x.baseDamage;
            x.magicNumber = x.baseMagicNumber;
            ((AbstractDynamicCard)x).defaultSecondMagicNumber = ((AbstractDynamicCard)x).defaultBaseSecondMagicNumber;
            x.block = x.baseBlock;
            x.initializeDescription();
            return x;
        }
        return new AlyssasBlade();
    }
    @Override
    public void applyPowers() {
        this.baseMagicNumber = this.misc;
        this.magicNumber = this.baseMagicNumber;
        super.applyPowers();
        this.initializeDescription();
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.isEthereal = false;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public Integer onSave() {
        return this.misc;
    }

    @Override
    public void onLoad(Integer misc) {
        this.misc = misc;
        this.applyPowers();
    }
}
