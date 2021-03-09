package theMarked.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import theMarked.DefaultMod;
import theMarked.actions.ImprovisedWeaponCheckAction;
import theMarked.actions.SummonImprovAction;
import theMarked.characters.TheMarked;

import static theMarked.DefaultMod.makeCardPath;

public class TakeOffHeels extends AbstractMarkedCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(TakeOffHeels.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill_TakeOffHeels.png");
    public static final String IMG_beta = makeCardPath("Skill_TakeOffHeels_beta.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheMarked.Enums.MARKED_GENTA;

    private static final int COST = 1;
    private static final int MAGIC = 2;


    // /STAT DECLARATION/


    public TakeOffHeels() {

        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        DefaultMod.loadJokeCardImage(this,IMG_beta);
        this.baseMagicNumber = MAGIC;
        this.magicNumber = baseMagicNumber;
        ImprovisedWeapon prev = new ImprovisedWeapon();
        prev.effect = 0;
        prev.initializeDescription();
        this.cardsToPreview = prev;
        this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p,p,new DexterityPower(p,magicNumber),magicNumber));

        this.addToBot(new SummonImprovAction(this.cardsToPreview,0,this.upgraded));
        this.addToBot(new ImprovisedWeaponCheckAction(this.upgraded,false));
    }


    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.cardsToPreview.upgrade();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
