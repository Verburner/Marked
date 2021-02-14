package theMarked.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theMarked.characters.TheMarked;

public class CastAnimationAction extends AbstractGameAction{
    private AbstractCard card;

    public CastAnimationAction(){}

    public void update() {

        if (AbstractDungeon.player instanceof TheMarked) {
            ((TheMarked) AbstractDungeon.player).castAnimation();
        }
        this.isDone = true;
    }
}