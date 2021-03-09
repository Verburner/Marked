package theMarked.cards;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import theMarked.DefaultMod;
import theMarked.actions.CastAnimationAction;
import theMarked.characters.TheMarked;

import static theMarked.DefaultMod.makeCardPath;

public class RepellingDischarge extends AbstractMarkedCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(RepellingDischarge.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill_RepellingDischarge.png");
    public static final String IMG_beta = makeCardPath("Skill_RepellingDischarge_beta.png");
    // /TEXT DECLARATION/


    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheMarked.Enums.MARKED_GENTA;

    private static final int COST = 1;
    private static final int MAGIC = 8;
    private static final int MAGIC2 = 6;
    private static final int UPGRADE_PLUS_MAGIC = 2;


    // /STAT DECLARATION/


    public RepellingDischarge() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        DefaultMod.loadJokeCardImage(this,IMG_beta);
        baseMagicNumber = MAGIC;
        magicNumber = baseMagicNumber;
        baseBlock = magicNumber;
        block = baseBlock;
        defaultBaseSecondMagicNumber = MAGIC2;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new CastAnimationAction());
        this.addToBot(new SFXAction("ATTACK_PIERCING_WAIL"));
        if (Settings.FAST_MODE) {
            this.addToBot(new VFXAction(p, new ShockWaveEffect(p.hb.cX, p.hb.cY, Settings.GREEN_TEXT_COLOR, ShockWaveEffect.ShockWaveType.CHAOTIC), 0.3F));
        } else {
            this.addToBot(new VFXAction(p, new ShockWaveEffect(p.hb.cX, p.hb.cY, Settings.GREEN_TEXT_COLOR, ShockWaveEffect.ShockWaveType.CHAOTIC), 1.5F));
        }

        this.applyPowers();
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        if (!p.hasPower("theMarked:MaelstromPower") && !upgraded) {
            this.addToBot(new ReducePowerAction(p, p, "theMarked:Charge",defaultSecondMagicNumber));
        }
        this.applyPowers();
    }

    @Override
    public void applyPowers()
    {
        int bblock = 0;
        if (AbstractDungeon.player.hasPower("theMarked:Charge")) {
            AbstractPower charge = AbstractDungeon.player.getPower("theMarked:Charge");
            if (charge != null) {
                bblock = charge.amount;
                if (bblock>defaultSecondMagicNumber) bblock = defaultSecondMagicNumber;
            }
        }
        this.baseBlock = magicNumber + bblock;
        super.applyPowers();
        if (bblock>0) this.isBlockModified = true;
        this.initializeDescription();
        this.baseBlock = magicNumber;
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDefaultSecondMagicNumber(UPGRADE_PLUS_MAGIC);
            this.baseBlock = baseMagicNumber;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
