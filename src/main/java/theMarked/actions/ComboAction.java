package theMarked.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Iterator;

public class ComboAction extends AbstractGameAction{

    private int amount;
    public ComboAction(int amount) {

        this.duration = Settings.ACTION_DUR_XFAST;
        this.amount = amount;
    }

    public void update() {
        ArrayList<AbstractCard> groupCopy = new ArrayList();
        Iterator var4 = AbstractDungeon.player.hand.group.iterator();

        while (var4.hasNext()) {
            AbstractCard c = (AbstractCard) var4.next();
            if (c.cost > 0 && c.costForTurn > 0 && !c.freeToPlayOnce && c.type == AbstractCard.CardType.ATTACK) {
                groupCopy.add(c);
            }
        }

        var4 = AbstractDungeon.actionManager.cardQueue.iterator();

        while (var4.hasNext()) {
            CardQueueItem i = (CardQueueItem) var4.next();
            if (i.card != null) {
                groupCopy.remove(i.card);
            }
        }

        AbstractCard c = null;
        if (groupCopy.isEmpty()) {
        } else {
            Iterator var9 = groupCopy.iterator();

            while (var9.hasNext()) {
                AbstractCard cc = (AbstractCard) var9.next();
            }

            c = (AbstractCard) groupCopy.get(AbstractDungeon.cardRandomRng.random(0, groupCopy.size() - 1));
        }

        if (c != null) {
            c.setCostForTurn(c.costForTurn - this.amount);
        } else {
        }

        this.isDone = true;
    }
}
