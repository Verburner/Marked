package theMarked.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theMarked.cards.AlyssasBlade;

import java.util.Iterator;

public class LevitateAction extends AbstractGameAction{
    private AbstractCard card;
    private int amount;

    public LevitateAction() {
        this.amount = -1;
        this.duration = Settings.ACTION_DUR_XFAST;
    }
    public LevitateAction(int amount) {
        this.amount = amount;
        this.duration = Settings.ACTION_DUR_XFAST;
    }

    public void update() {
        Iterator var1 = AbstractDungeon.player.hand.group.iterator();

        while(var1.hasNext()) {
            AbstractCard c = (AbstractCard)var1.next();
            if (c.type == AbstractCard.CardType.SKILL) {
                if (this.amount == -1) c.setCostForTurn(-1);
                else c.setCostForTurn(c.costForTurn-this.amount);
            }
        }

        this.isDone = true;
    }
}
