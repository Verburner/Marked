package theMarked.cards;

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
import theMarked.actions.SummonAction;
import theMarked.actions.SwordBuffAction;
import theMarked.characters.TheMarked;

import static theMarked.DefaultMod.makeCardPath;

public class EmpowerWeapon extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(EmpowerWeapon.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill_EmpowerWeapon.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheMarked.Enums.MARKED_GENTA;

    private static final int COST = 1;
    private static final int MAGIC = 5;

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;


    // /STAT DECLARATION/


    public EmpowerWeapon() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.cardsToPreview = new AlyssasBlade();
        this.baseMagicNumber = MAGIC;
        this.magicNumber = baseMagicNumber;
        this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard sword = this.cardsToPreview;
        AbstractDungeon.actionManager.addToBottom(new SummonAction(sword));
        AbstractDungeon.actionManager.addToBottom(new SwordBuffAction(magicNumber));
        this.addToBot(new VFXAction(new SwordBuffEffect(p.hb.cX, p.hb.cY, CardHelper.getColor(240.0F,180.0F,180.0F))));
        AbstractDungeon.actionManager.addToBottom(new SFXAction("BUFF_2"));
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
    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.exhaust = false;
            //upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
