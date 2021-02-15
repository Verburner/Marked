package theMarked.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NoDrawPower;
import theMarked.DefaultMod;
import theMarked.characters.TheMarked;

import static theMarked.DefaultMod.makeCardPath;

public class Convert extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(Convert.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill_Convert.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheMarked.Enums.MARKED_GENTA;

    private static final int COST = 0;


    // /STAT DECLARATION/


    public Convert() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int amt = 0;
        if (p.hasPower("theMarked:Charge")) {
            for (int i = 0; i < p.getPower("theMarked:Charge").amount; i++) {
                amt++;
            }
            if (amt > 5) amt = 5;
            if (!p.hasPower("theMarked:MaelstromPower") && !this.upgraded) {
                this.addToBot(new ReducePowerAction(p, p, ("theMarked:Charge"), amt));
            }
        }
        this.addToBot(new DrawCardAction(amt));
        this.addToBot(new ApplyPowerAction(p, p, new NoDrawPower(p)));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgraded = true;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
