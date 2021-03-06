package theMarked.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theMarked.DefaultMod;
import theMarked.characters.TheMarked;
import theMarked.powers.RogueSparksPower;

import static theMarked.DefaultMod.makeCardPath;

public class RogueSparks extends AbstractMarkedCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Weirdness Apply X (+1) keywords to yourself.
     */

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(RogueSparks.class.getSimpleName());
    public static final String IMG = makeCardPath("Power_RogueSparks.png");
    public static final String IMG_beta = makeCardPath("Power_RogueSparks_beta.png");

    public static final int MAGIC = 2;
    public static final int MAGIC2 = 1;
    public static final int UPGRADE_PLUS_MAGIC = 1;

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheMarked.Enums.MARKED_GENTA;

    private static final int COST = 2;

    // /STAT DECLARATION/

    public RogueSparks() {

        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        DefaultMod.loadJokeCardImage(this,IMG_beta);
        this.baseMagicNumber = MAGIC;
        this.magicNumber = baseMagicNumber;
        this.defaultBaseSecondMagicNumber = MAGIC2;
        this.defaultSecondMagicNumber = defaultBaseSecondMagicNumber;

    }
    
    // Actions the card should do.
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new RogueSparksPower(p,p,magicNumber,defaultSecondMagicNumber),magicNumber));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            upgradeDefaultSecondMagicNumber(UPGRADE_PLUS_MAGIC);
            initializeDescription();
        }
    }
}