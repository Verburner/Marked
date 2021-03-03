package theMarked.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theMarked.DefaultMod;
import theMarked.actions.SummonCircuitAction;
import theMarked.characters.TheMarked;

import static theMarked.DefaultMod.makeCardPath;

public class PowerFlow extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(PowerFlow.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill_PowerFlow.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheMarked.Enums.MARKED_GENTA;

    private static final int COST = 0;
    private static final int MAGIC = 4;
    private static final int UPGRADE_PLUS_MAGIC = 2;


    // /STAT DECLARATION/


    public PowerFlow() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.cardsToPreview = new Circuit();
        this.baseMagicNumber = MAGIC;
        this.magicNumber = baseMagicNumber;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard charge = this.cardsToPreview;
        AbstractDungeon.actionManager.addToBottom(new SummonCircuitAction(charge, magicNumber, upgraded));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            initializeDescription();
        }
    }
}
