package theMarked.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theMarked.cards.AlyssasBlade;

import java.util.Iterator;

public class ChannelAction extends AbstractGameAction{
    private AbstractCard card;

    public ChannelAction(int amount) {
        this.amount = amount;
    }

    public void update() {
        int count = AbstractDungeon.player.hand.size();
        int multiplyer = 0;
        for (AbstractCard c : AbstractDungeon.player.hand.group)
        {
            if (c.type == AbstractCard.CardType.ATTACK)
            {
                multiplyer++;
                this.addToTop(new ExhaustSpecificCardAction(c,AbstractDungeon.player.hand));
            }
        }
        this.addToBot(new SummonAction(new AlyssasBlade()));
        this.addToBot(new SwordBuffAction(this.amount*multiplyer));
        this.isDone = true;
    }
}
