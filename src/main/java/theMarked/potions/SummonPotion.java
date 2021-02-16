package theMarked.potions;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theMarked.VFX.SwordBuffEffect;
import theMarked.actions.SummonAction;
import theMarked.actions.SwordBuffAction;
import theMarked.cards.AlyssasBlade;

public class SummonPotion extends AbstractPotion {


    public static final String POTION_ID = theMarked.DefaultMod.makeID("SummonPotion");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public SummonPotion() {
        // The bottle shape and inside is determined by potion size and color. The actual colors are the main DefaultMod.java
        super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.CARD, PotionColor.FIRE);
        
        // Potency is the damage/magic number equivalent of potions.
        potency = getPotency();
        
        // Initialize the Description
        description = DESCRIPTIONS[0] + potency + DESCRIPTIONS[1];
        
       // Do you throw this potion at an enemy or do you just consume it.
        isThrown = false;
        
        // Initialize the on-hover name + description
        tips.add(new PowerTip(name, description));
        
    }


    @Override
    public void use(AbstractCreature target) {
        target = AbstractDungeon.player;
        // If you are in combat, gain strength and the "lose strength at the end of your turn" power, equal to the potency of this potion.
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            AbstractDungeon.actionManager.addToBottom(new SummonAction(new AlyssasBlade()));
            AbstractDungeon.actionManager.addToBottom(new SwordBuffAction(potency));

            this.addToBot(new VFXAction(new SwordBuffEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, CardHelper.getColor(240.0F,185.0F,200.0F))));
            AbstractDungeon.actionManager.addToBottom(new SFXAction("BUFF_2"));
        }
    }
    
    @Override
    public AbstractPotion makeCopy() {
        return new SummonPotion();
    }

    // This is your potency.
    @Override
    public int getPotency(final int potency) {
        return 8;
    }

    public void upgradePotion()
    {
      potency += 8;
      tips.clear();
      tips.add(new PowerTip(name, description));
    }
}
