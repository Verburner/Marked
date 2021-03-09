package theMarked.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theMarked.DefaultMod;
import theMarked.characters.TheMarked;
import theMarked.powers.ChargePower;

import java.util.Iterator;

import static theMarked.DefaultMod.makeCardPath;

public class Aftermath extends AbstractMarkedCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(Aftermath.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill_Aftermath.png");
    public static final String IMG_beta = makeCardPath("Skill_Aftermath_beta.png");
    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheMarked.Enums.MARKED_GENTA;

    private static final int COST = 0;
    private static final int MAGIC = 2;
    private static final int UPGRADE_PLUS_MAGIC = 1;


    // /STAT DECLARATION/


    public Aftermath() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        DefaultMod.loadJokeCardImage(this,IMG_beta);
        this.baseMagicNumber = MAGIC;
        this.magicNumber = baseMagicNumber;
        this.defaultBaseSecondMagicNumber = 0;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new ChargePower(p,p,magicNumber),magicNumber));
        if (defaultSecondMagicNumber>0)
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new ChargePower(p,p,magicNumber*defaultSecondMagicNumber),magicNumber*defaultSecondMagicNumber));
    }

    public void applyPowers() {
        super.applyPowers();
        Iterator var2 = AbstractDungeon.actionManager.cardsPlayedThisTurn.iterator();
        defaultBaseSecondMagicNumber = 0;
        while (var2.hasNext()) {
            AbstractCard c = (AbstractCard) var2.next();
            if (c.type == CardType.ATTACK) {
                ++defaultBaseSecondMagicNumber;
            }
        }
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber;
        initializeDescription();
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
