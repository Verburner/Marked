package theMarked.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import theMarked.DefaultMod;
import theMarked.actions.CastAnimationAction;
import theMarked.characters.TheMarked;
import theMarked.powers.CreepingChasmPower;

import static theMarked.DefaultMod.makeCardPath;

public class CreepingChasm extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(CreepingChasm.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill_CreepingChasm.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION[0];
    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheMarked.Enums.MARKED_GENTA;

    private static final int COST = 1;
    private static final int MAGIC_NUMBER = 17;
    private static final int UPGRADE_PLUS_MAGIC = 3;
    private static final int MAGIC2 = 1;
    private static final int UPGRADE_MAGIC2 = 1;


    // /STAT DECLARATION/


    public CreepingChasm() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.baseMagicNumber = MAGIC_NUMBER;
        this.magicNumber = baseMagicNumber;
        this.defaultBaseSecondMagicNumber = MAGIC2;
        this.defaultSecondMagicNumber = defaultBaseSecondMagicNumber;
        this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        this.addToBot(new CastAnimationAction());
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters)
        {
            if (!mo.isDead)
            {
                this.addToBot(new ApplyPowerAction(mo,p,new WeakPower(mo,defaultSecondMagicNumber,false)));
            }
        }
        this.addToBot(new ApplyPowerAction(p, p, new CreepingChasmPower(p,p, 1, this.magicNumber), this.magicNumber));
        if (!p.hasPower("theMarked:MaelstromPower")) {
            this.addToBot(new ReducePowerAction(p, p, ("theMarked:Charge"), 4));
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        } else if (!p.hasPower("theMarked:Charge") || p.getPower("theMarked:Charge").amount < 4) {
            this.cantUseMessage = EXTENDED_DESCRIPTION;
            return false;
        } else {
            return canUse;
        }
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            upgradeDefaultSecondMagicNumber(UPGRADE_MAGIC2);
            initializeDescription();
        }
    }
}
