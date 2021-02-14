package theMarked.cards;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theMarked.DefaultMod;
import theMarked.VFX.SwordBuffEffect;
import theMarked.actions.SummonAction;
import theMarked.actions.SwordWeakAction;
import theMarked.characters.TheMarked;

import static theMarked.DefaultMod.makeCardPath;

public class ParalyzingBlade extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(ParalyzingBlade.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill_ParalyzingBlade.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheMarked.Enums.MARKED_GENTA;

    private static final int COST = 2;
    private static final int BLOCK = 9;
    private static final int UPGRADE_PLUS_MAGIC = 1;


    // /STAT DECLARATION/


    public ParalyzingBlade() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.cardsToPreview = new AlyssasBlade();
        this.baseBlock = BLOCK;
        this.block = baseBlock;
        this.baseMagicNumber = 1;
        this.magicNumber = baseMagicNumber;
        this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard sword = this.cardsToPreview;
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p,this.block));
        AbstractDungeon.actionManager.addToBottom(new SummonAction(sword));
        AbstractDungeon.actionManager.addToBottom(new SwordWeakAction(magicNumber));

        this.addToBot(new VFXAction(new SwordBuffEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, CardHelper.getColor(200.0F,240.0F,170.0F))));
        AbstractDungeon.actionManager.addToBottom(new SFXAction("SPHERE_DETECT_VO_1"));

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
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            upgradeBlock(UPGRADE_PLUS_MAGIC);
            initializeDescription();
        }
    }
}
