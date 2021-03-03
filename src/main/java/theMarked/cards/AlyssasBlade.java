package theMarked.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theMarked.DefaultMod;
import theMarked.characters.TheMarked;
import theMarked.powers.BanishPower;

import java.util.Iterator;

import static theMarked.DefaultMod.makeCardPath;

public class AlyssasBlade extends AbstractMarkedCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Special Strike: Deal 9(+3) damage.
     */

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(AlyssasBlade.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack_AlyssasBlade.png");
    public static final String IMG_beta = makeCardPath("Attack_AlyssasBlade_beta.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION_PLUS1 = cardStrings.EXTENDED_DESCRIPTION[0];
    public static final String DESCRIPTION_PLUS2 = cardStrings.EXTENDED_DESCRIPTION[1];
    public static final String DESCRIPTION_PLUS3 = cardStrings.EXTENDED_DESCRIPTION[2];

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = 1;
    private static final int DAMAGE = 10;
    private static final int UPGRADE_PLUS_DMG = 3;

    public int specialDamage;

    // /STAT DECLARATION/

    public AlyssasBlade() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        DefaultMod.loadJokeCardImage(this,IMG_beta);
        baseDamage = DAMAGE;
        baseMagicNumber = 0;
        defaultBaseSecondMagicNumber = 0;
        baseBlock = 0;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        // For each energy, create 1 damage action.
            magicNumber = baseMagicNumber;
            defaultSecondMagicNumber = defaultBaseSecondMagicNumber;

            AbstractDungeon.actionManager.addToBottom(
                    new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                            AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            if (magicNumber>0) {
                this.addToBot(new ApplyPowerAction(m, p, new WeakPower(m, this.magicNumber, false), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
            }
            if (defaultSecondMagicNumber>0) {
                this.addToBot(new ApplyPowerAction(m, p, new BanishPower(m, p, this.defaultSecondMagicNumber), this.defaultSecondMagicNumber, true, AbstractGameAction.AttackEffect.NONE));
            }
    }
    @Override
    public void initializeDescription()
    {
        String baseRawDescription = rawDescription;
        if (baseMagicNumber > 0) rawDescription += DESCRIPTION_PLUS1;
        if (defaultBaseSecondMagicNumber > 0) rawDescription += DESCRIPTION_PLUS2;
        if (baseBlock > 0) rawDescription += DESCRIPTION_PLUS3;
        super.initializeDescription();
        rawDescription = baseRawDescription;
    }

    public void triggerOnExhaust() {
        this.baseDamage = DAMAGE;
        if (upgraded) this.baseDamage += UPGRADE_PLUS_DMG;
        this.baseMagicNumber = 0;
        this.defaultBaseSecondMagicNumber = 0;
        this.baseBlock = 0;

        this.damage = baseDamage;
        this.magicNumber = 0;
        this.defaultSecondMagicNumber = 0;
        this.block = 0;

        if (AbstractDungeon.player instanceof TheMarked) {
            ((TheMarked) AbstractDungeon.player).loseSword();
        }

        this.initializeDescription();
    }

    @Override
    public AbstractCard makeStatEquivalentCopy()
    {
        if (this.isInCombat()) {
            AbstractCard card = this.makeCopy();
            if (doIexist()) card = new MirrorBlade();

            for (int i = 0; i < this.timesUpgraded; ++i) {
                card.upgrade();
            }
            card.target = this.target;
            card.upgraded = this.upgraded;
            card.timesUpgraded = this.timesUpgraded;
            card.baseDamage = this.baseDamage;
            card.baseBlock = this.baseBlock;
            card.baseMagicNumber = this.baseMagicNumber;
            ((AbstractDynamicCard) card).defaultBaseSecondMagicNumber = this.defaultBaseSecondMagicNumber;
            card.cost = this.cost;
            card.costForTurn = this.costForTurn;
            card.isCostModified = this.isCostModified;
            card.isCostModifiedForTurn = this.isCostModifiedForTurn;
            card.inBottleLightning = this.inBottleLightning;
            card.inBottleFlame = this.inBottleFlame;
            card.inBottleTornado = this.inBottleTornado;
            card.isSeen = this.isSeen;
            card.isLocked = this.isLocked;
            card.misc = this.misc;
            card.freeToPlayOnce = this.freeToPlayOnce;

            card.initializeDescription();
            return card;
        }
        else return super.makeStatEquivalentCopy();
    }
    // Upgraded stats.
    public static boolean isInCombat() {
        return CardCrawlGame.isInARun() && AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT;
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }

    private boolean doIexist()
    {
        AbstractCard c;
        Iterator var1 = AbstractDungeon.player.discardPile.group.iterator();
        while(var1.hasNext()) {
            c = (AbstractCard) var1.next();
            if (c instanceof AlyssasBlade) return true;
        }
        var1 = AbstractDungeon.player.drawPile.group.iterator();
        while(var1.hasNext()) {
            c = (AbstractCard) var1.next();
            if (c instanceof AlyssasBlade) return true;
        }
        var1 = AbstractDungeon.player.limbo.group.iterator();
        while(var1.hasNext()) {
            c = (AbstractCard) var1.next();
            if (c instanceof AlyssasBlade) return true;
        }
        var1 = AbstractDungeon.player.hand.group.iterator();
        while(var1.hasNext()) {
            c = (AbstractCard) var1.next();
            if (c instanceof AlyssasBlade) return true;
        }
        for (CardQueueItem cq : AbstractDungeon.actionManager.cardQueue) {
            if (cq.card instanceof AlyssasBlade)
            {
                return true;
            }
        }
        return false;
    }
    @Override
    public AbstractCard makeCopy() {
        return new AlyssasBlade();
    }
}