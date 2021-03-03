package theMarked.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Iterator;

public class ChallengeReduceDamageAction extends AbstractGameAction{

    private int amount;
    public ChallengeReduceDamageAction() {

        this.duration = Settings.ACTION_DUR_XFAST;
    }

    public void update() {
        AbstractDungeon.onModifyPower();
        this.isDone = true;
    }
}
