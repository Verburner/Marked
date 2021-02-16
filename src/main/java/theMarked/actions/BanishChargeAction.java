package theMarked.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import theMarked.powers.ChargePower;

public class BanishChargeAction extends AbstractGameAction{

    public BanishChargeAction(AbstractCreature target, AbstractCreature source) {
        this.target = target;
        this.source = source;
    }
    public void update() {
            if (source.hasPower("theMarked:BanishPower"))
            {
                this.addToTop(
                        new ApplyPowerAction(this.target,
                                             this.target,
                                             new ChargePower(this.target, this.target, this.source.getPower("theMarked:BanishPower").amount),
                                             this.source.getPower("theMarked:BanishPower").amount));
            }
            this.isDone = true;
        }

}
