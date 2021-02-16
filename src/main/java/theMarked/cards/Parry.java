package theMarked.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theMarked.DefaultMod;
import theMarked.actions.SummonAction;
import theMarked.actions.SwordBuffAction;
import theMarked.characters.TheMarked;

import static theMarked.DefaultMod.makeCardPath;

public class Parry extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(Parry.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill_Parry.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheMarked.Enums.MARKED_GENTA;

    private static final int COST = 1;
    private static final int BLOCK = 8;
    private static final int MAGIC = 2;


    // /STAT DECLARATION/


    public Parry() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.baseBlock = BLOCK;
        this.block = baseBlock;
        this.baseMagicNumber = MAGIC;
        this.magicNumber = baseMagicNumber;
        this.cardsToPreview = new AlyssasBlade();
    }

    @Override
    public void hover()
    {
        if (AbstractDungeon.getCurrMapNode() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            this.cardsToPreview = this.searchBlade2();
        } else this.cardsToPreview = new AlyssasBlade();
        super.hover();
    }
    // Actions the card should do.

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new SummonAction(new AlyssasBlade()));
        AbstractCard c = this.searchBlade();
        if (c== null) this.addToBot(new SwordBuffAction(-magicNumber));
        else this.addToBot(new SwordBuffAction(-((int)(c.baseDamage*(((float)magicNumber)/10.0F)))));
        //this.addToBot(new DiscardSwordAction());
        this.addToBot(new GainBlockAction(p, block));
    }
    @Override
    public void applyPowers()
    {
        AbstractCard c = this.searchBlade();
        if (c==null)
        {
            this.baseBlock = 10-this.magicNumber;
            if (upgraded) this.baseBlock += this.magicNumber;
        }
        else
        {
            this.baseBlock = c.baseDamage-((int)(c.baseDamage*(((float)magicNumber)/10.0F)));
            if (upgraded) this.baseBlock += ((int)(c.baseDamage*(((float)magicNumber)/10.0F)));
        }
        super.applyPowers();
        initializeDescription();
    }
    private AbstractCard searchBlade2()
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
            return x;
        }
        return null;
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
            return x;
        }
        return null;
    }
    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            upgradeBlock(magicNumber);
            initializeDescription();
        }
    }
}
