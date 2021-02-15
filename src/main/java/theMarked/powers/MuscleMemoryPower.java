package theMarked.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.BetterOnApplyPowerPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.BetterOnExhaustPower;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theMarked.DefaultMod;
import theMarked.VFX.SwordBuffEffect;
import theMarked.actions.SummonAction;
import theMarked.actions.SwordBuffAction;
import theMarked.cards.AlyssasBlade;
import theMarked.util.TextureLoader;

import javax.smartcardio.Card;

import static theMarked.DefaultMod.makePowerPath;

//Consume and deal damage equal to amount on attack.

public class MuscleMemoryPower extends AbstractPower implements CloneablePowerInterface, BetterOnExhaustPower {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("MuscleMemoryPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("SoldierPower84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("SoldierPower32.png"));

    public MuscleMemoryPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }
    /*
    // On use card, apply (amount) of Dexterity. (Go to the actual power card for the amount.)
    public void onExhaust(AbstractCard card) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            this.flash();
            this.addToBot(new SummonAction(new AlyssasBlade()));
            this.addToBot(new SwordBuffAction(this.amount));

            this.addToBot(new VFXAction(new SwordBuffEffect(owner.hb.cX, owner.hb.cY, CardHelper.getColor(250.0F, 250.0F, 250.0F))));
            AbstractDungeon.actionManager.addToBottom(new SFXAction("BUFF_2"));
        }
    }
    */

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0]+amount+DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new MuscleMemoryPower(owner, source, amount);
    }

    @Override
    public void betterOnExhaust(CardGroup cardGroup, AbstractCard card) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            this.flash();
            this.addToBot(new SummonAction(new AlyssasBlade()));
            this.addToBot(new SwordBuffAction(this.amount));

            this.addToBot(new VFXAction(new SwordBuffEffect(owner.hb.cX, owner.hb.cY, CardHelper.getColor(250.0F, 250.0F, 250.0F))));
            AbstractDungeon.actionManager.addToBottom(new SFXAction("BUFF_2"));
        }
    }
}
