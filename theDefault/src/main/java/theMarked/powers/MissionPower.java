package theMarked.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.BetterOnApplyPowerPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import theMarked.DefaultMod;
import theMarked.actions.BanishLoseHpAction;
import theMarked.relics.NailFile;
import theMarked.util.TextureLoader;

import java.util.Iterator;

import static theMarked.DefaultMod.makePowerPath;

//Consume and deal damage equal to amount on attack.

public class MissionPower extends AbstractPower implements CloneablePowerInterface, BetterOnApplyPowerPower {
    public AbstractCreature source;
    public final boolean upgraded;

    public static final String POWER_ID = DefaultMod.makeID("MissionPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("MissionPower84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("MissionPower32.png"));

    public MissionPower(final AbstractCreature owner, final AbstractCreature source, final int amount, final boolean upgraded) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;
        this.upgraded = upgraded;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0]+amount+DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new MissionPower(owner, source, amount, upgraded);
    }

    @Override
    public boolean betterOnApplyPower(AbstractPower pow, AbstractCreature target, AbstractCreature source) {
        if (pow instanceof BanishPower && hasDebuff(source))
        {
            pow.amount += this.amount;
            if (pow.amount >= 10 && !pow.owner.hasPower(ArtifactPower.POWER_ID)) {
                pow.flash();
                this.addToTop(new BanishLoseHpAction(pow.owner, pow.owner, 50, AbstractGameAction.AttackEffect.POISON));
                pow.amount = 0;
                this.addToTop(new RemoveSpecificPowerAction(pow.owner, pow.owner,BanishPower.POWER_ID));
            }
            this.flash();
        }
        return true;
    }

    @Override
    public int betterOnApplyPowerStacks(AbstractPower pow, AbstractCreature target, AbstractCreature source, int stackAmount) {
        if (pow instanceof BanishPower && hasDebuff(target))
        {
            stackAmount += this.amount;
            this.flash();
        }
        return stackAmount;
    }
    private boolean hasDebuff(AbstractCreature c)
    {
        for (AbstractPower p : c.powers)
        {
            if (p.type == PowerType.DEBUFF) return true;
        }
        return false;
    }
}
