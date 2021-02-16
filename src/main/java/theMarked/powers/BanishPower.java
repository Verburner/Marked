package theMarked.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theMarked.DefaultMod;
import theMarked.actions.BanishLoseHpAction;
import theMarked.relics.DemonDagger;
import theMarked.util.TextureLoader;

import static theMarked.DefaultMod.makePowerPath;

//Consume and deal damage equal to amount on attack.

public class BanishPower extends AbstractPower implements CloneablePowerInterface, OnReceivePowerPower {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("BanishPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private int trigger;
    private AbstractRelic rel;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("BanishPower84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("BanishPower32.png"));
    public BanishPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;
        this.trigger = 10;
        this.rel = null;

        if (AbstractDungeon.player.hasRelic(DemonDagger.ID))
        {
            if (!((DemonDagger)AbstractDungeon.player.getRelic(DemonDagger.ID)).triggered)
            {
                this.amount += 2;
            }
        }

        type = PowerType.DEBUFF;
        isTurnBased = false;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.updateDescription();

        if (AbstractDungeon.player.hasPower(DeterminationPower.POWER_ID) && !this.owner.hasPower(ArtifactPower.POWER_ID))
        {
            DeterminationPower p2 = ((DeterminationPower)AbstractDungeon.player.getPower(DeterminationPower.POWER_ID));
            if (p2.applied < p2.times) {
                p2.applied++;
                p2.flash();
                this.addToBot(new DrawCardAction(AbstractDungeon.player, p2.amount, false));
            }
        }
        if (this.amount >= trigger && !this.owner.hasPower(ArtifactPower.POWER_ID)) {
            this.flash();
            this.addToTop(new BanishLoseHpAction(owner, owner, 50, AbstractGameAction.AttackEffect.POISON));
            if (rel != null) rel.flash();
            this.amount = 0;
            this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner,BanishPower.POWER_ID));
        }
    }

    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        this.updateDescription();
        if (this.amount >= trigger) {
            this.flash();
            this.addToTop(new BanishLoseHpAction(owner, owner, 50, AbstractGameAction.AttackEffect.POISON));
            if (rel != null) rel.flash();
            this.amount = 0;
            this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner,BanishPower.POWER_ID));
        }
    }

    @Override
    public boolean onReceivePower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power instanceof BanishResistancePower && target == this.owner) {
            this.flash();
            trigger = 10 + power.amount;
            this.updateDescription();
        }
        return true;
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + 10 + DESCRIPTIONS[1];
    }
    @Override
    public AbstractPower makeCopy() {
        return new BanishPower(owner, source, amount);
    }
}
