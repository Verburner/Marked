package theMarked.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theMarked.powers.ChargePower;

public class EyepatchAction extends AbstractGameAction{
    private AbstractRelic r;

    public EyepatchAction(AbstractRelic r) {
        this.r = r;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        int eneToGain = 0;

        for (AbstractPower pow : p.powers)
        {
            if (pow.type == AbstractPower.PowerType.DEBUFF) {
                eneToGain=1;
            }
        }
        if (eneToGain>0)
        {
            r.flash();
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(eneToGain));
        }
        this.isDone = true;
    }
}
