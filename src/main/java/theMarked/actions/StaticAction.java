package theMarked.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theMarked.powers.ChargePower;

public class StaticAction extends AbstractGameAction{

    public StaticAction() {
    }
    public void update() {

            int chargeToGain = AbstractDungeon.player.currentBlock/2;
            this.addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new ChargePower(AbstractDungeon.player,AbstractDungeon.player,chargeToGain),chargeToGain));

            this.isDone = true;
        }
}
