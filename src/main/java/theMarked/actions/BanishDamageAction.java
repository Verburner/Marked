package theMarked.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theMarked.powers.ChargePower;

public class BanishDamageAction extends AbstractGameAction{

    public BanishDamageAction(AbstractCreature target) {
        this.target = target;
    }
    public void update() {
            if (target.hasPower("theMarked:BanishPower"))
            {
                    target.damage(new DamageInfo(target, target.getPower("theMarked:BanishPower").amount, DamageInfo.DamageType.THORNS));
            }
            this.isDone = true;
        }
}
