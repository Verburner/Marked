package theMarked.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.BetterOnApplyPowerPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import theMarked.DefaultMod;
import theMarked.util.TextureLoader;

import java.util.Iterator;

import static theMarked.DefaultMod.makePowerPath;

//Consume and deal damage equal to amount on attack.

public class HeritagePower extends AbstractPower implements CloneablePowerInterface, BetterOnApplyPowerPower {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("HeritagePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("HeritagePower84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("HeritagePower32.png"));

    public HeritagePower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;
        this.priority = 2;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    // On use card, apply (amount) of Dexterity. (Go to the actual power card for the amount.)
    /*
    public void atStartOfTurn() {
        this.flash();
        Iterator var3 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();

        while(var3.hasNext()) {
            AbstractMonster mo = (AbstractMonster)var3.next();
            if (!mo.isDead) {
                this.addToBot(new ApplyPowerAction(mo, owner, new BanishPower(mo, owner, this.amount), this.amount, true, AbstractGameAction.AttackEffect.NONE));
            }
        }
    }
    */


    @Override
    public boolean betterOnApplyPower(AbstractPower pow, AbstractCreature abstractCreature, AbstractCreature abstractCreature1) {
        if (pow instanceof BanishPower)
        {
            /*
            this.addToBot(new GainBlockAction(this.owner, this.amount));
            this.flash();
            */
        }
        return true;
    }


    @Override
    public int betterOnApplyPowerStacks(AbstractPower pow, AbstractCreature target, AbstractCreature source, int stackAmount) {
        if (pow instanceof BanishPower && !target.hasPower(ArtifactPower.POWER_ID))
        {
            this.addToBot(new GainBlockAction(this.owner, this.amount));
            this.flash();
        }
        return stackAmount;
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0]+amount+DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new HeritagePower(owner, source, amount);
    }
}
