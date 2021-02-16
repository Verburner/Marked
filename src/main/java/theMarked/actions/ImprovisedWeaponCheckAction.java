package theMarked.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import theMarked.cards.Circuit;
import theMarked.cards.ImprovisedWeapon;
import theMarked.powers.ChargePower;

import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

public class ImprovisedWeaponCheckAction extends AbstractGameAction{
    private boolean upgraded;
    public boolean cycle;

    public ImprovisedWeaponCheckAction(boolean upgraded, boolean cycle) {

        this.upgraded = upgraded;
        this.cycle = cycle;
    }

    public void update() {
        if (!cycle)
        {
            this.addToBot(new ImprovisedWeaponCheckAction(this.upgraded,true));
        }
        else {
            int i = 0;
            int s = BaseMod.MAX_HAND_SIZE - AbstractDungeon.player.hand.size();
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c instanceof ImprovisedWeapon) {
                    i++;
                }
            }
            if (i < 2 && s > 0) {
                ImprovisedWeapon imp = new ImprovisedWeapon();
                if (upgraded) imp.upgrade();
                this.addToBot(new MakeTempCardInHandAction(imp));
            }
        }
        this.isDone = true;
    }
}