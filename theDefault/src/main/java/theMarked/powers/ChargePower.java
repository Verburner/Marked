package theMarked.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theMarked.DefaultMod;
import theMarked.cards.*;
import theMarked.util.TextureLoader;

import static theMarked.DefaultMod.makePowerPath;

//Consume and deal damage equal to amount on attack.

public class ChargePower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("Charge");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("ChargePower84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("ChargePower32.png"));

    public ChargePower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
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

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        int bdmg = this.amount;

        if (bdmg>6 && !(card instanceof Circuit) && !(card instanceof Electrocute))
        {
            bdmg = 6;
        }
        if (card instanceof Electrocute)
        {
            if (card.upgraded) bdmg *= 3;
            else bdmg *= 2;
        }
        return type == DamageInfo.DamageType.NORMAL ? damage + (float)bdmg : damage;
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            this.flash();
            if (!(card instanceof Circuit || this.owner.hasPower("theMarked:MaelstromPower") || card instanceof AlyssasBlade && card.baseBlock == 1 || card instanceof MirrorBlade && card.baseBlock == 1 || card instanceof LightningStrikesTwice))
            {
                if (AbstractDungeon.player.hasRelic("theMarked:Gun"))
                {
                    this.addToBot(new ReducePowerAction(this.owner, this.owner, "theMarked:Charge",3));
                    AbstractDungeon.player.getRelic("theMarked:Gun").flash();
                }
                else this.addToBot(new ReducePowerAction(this.owner, this.owner, "theMarked:Charge",6));
            }
        }
    }

    // Note: If you want to apply an effect when a power is being applied you have 3 options:
    //onInitialApplication is "When THIS power is first applied for the very first time only."
    //onApplyPower is "When the owner applies a power to something else (only used by Sadistic Nature)."
    //onReceivePowerPower from StSlib is "When any (including this) power is applied to the owner."

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new ChargePower(owner, source, amount);
    }
}
