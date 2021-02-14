package theMarked.cards;

import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theMarked.DefaultMod;
import theMarked.actions.ImprovisedWeaponCheckAction;
import theMarked.actions.SummonImprovAction;
import theMarked.characters.TheMarked;

import static theMarked.DefaultMod.makeCardPath;

public class Improvise extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Special Strike: Deal 9(+3) damage.
     */

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(Improvise.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill_Improvise.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheMarked.Enums.MARKED_GENTA;

    private static final int COST = 1;

    public int specialDamage;

    // /STAT DECLARATION/

    public Improvise() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        ImprovisedWeapon prev = new ImprovisedWeapon();
        prev.effect = 0;
        prev.initializeDescription();
        this.cardsToPreview = prev;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ExhaustAction(1, false));
        this.addToBot(new SummonImprovAction(this.cardsToPreview,0,false));
        this.addToBot(new ImprovisedWeaponCheckAction(false,false));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
            initializeDescription();
        }
    }
}