package theMarked.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theMarked.powers.BanishPower;
import theMarked.powers.MissionPower;
import theMarked.relics.DemonDagger;

public class SealAction extends AbstractGameAction{
    private AbstractCard card;
    private int cost;

    public SealAction(int amount, AbstractCard card, AbstractMonster target, int cost) {
        this.amount = amount;
        this.target = target;
        this.card = card;
        this.cost = cost;
    }

    public void update() {
        int trigger = 10;
        int toApply = this.amount;
        if (AbstractDungeon.player.hasPower(MissionPower.POWER_ID))
        {
            boolean hasDebuff = false;
            for (AbstractPower p : AbstractDungeon.player.powers)
            {
                if (p.type == AbstractPower.PowerType.DEBUFF) hasDebuff = true;
            }
            if (hasDebuff) toApply += AbstractDungeon.player.getPower(MissionPower.POWER_ID).amount;
        }
        if (AbstractDungeon.player.hasRelic(DemonDagger.ID))
        {
            if (!((DemonDagger)AbstractDungeon.player.getRelic(DemonDagger.ID)).triggered)
            {
                toApply += 2;
            }
        }
        if (target.hasPower(BanishPower.POWER_ID)) {
            if (target.getPower(BanishPower.POWER_ID).amount + toApply >= trigger) {
                AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(cost));
                AbstractDungeon.actionManager.addToBottom(new DiscardToHandAction(this.card));
            }
        }
        else if (toApply > trigger)
        {
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(cost));
            AbstractDungeon.actionManager.addToBottom(new DiscardToHandAction(this.card));
        }
        this.isDone = true;
    }
}