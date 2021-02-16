package theMarked.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import theMarked.DefaultMod;
import theMarked.cards.Circuit;

import java.util.ArrayList;

public class ElectrifyAction extends AbstractGameAction {
    private AbstractPlayer p;
    private ArrayList<AbstractCard> cannotDuplicate = new ArrayList<>();

    public ElectrifyAction(int amount) {
        this.amount = amount;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = 0.25F;
        this.p = AbstractDungeon.player;
    }


    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            for (AbstractCard c : p.hand.group) {
                if (c.type != AbstractCard.CardType.ATTACK) {
                    this.cannotDuplicate.add(c);
                }
            }

            if (this.cannotDuplicate.size() == this.p.hand.group.size()) {
                this.isDone = true;
                return;
            }

            if (this.p.hand.group.size() - this.cannotDuplicate.size() == 1) {
                for (AbstractCard c : p.hand.group) {
                    if (c.type == AbstractCard.CardType.ATTACK) {
                        this.addToBot(new ExhaustSpecificCardAction(c, p.hand));
                        this.addToBot(new MakeTempCardInHandAction(new Circuit(), amount, true));
                        this.isDone = true;
                        return;
                    }
                }
            }

            this.p.hand.group.removeAll(this.cannotDuplicate);
            if (this.p.hand.group.size() > 1) {
                AbstractDungeon.handCardSelectScreen.open(CardCrawlGame.languagePack.getUIString(DefaultMod.makeID("ElectrifyAction")).TEXT[0], 1, false, false, false, false);
                this.tickDuration();
                return;
            }
            /*
            if (this.p.hand.group.size() == 1) {
                AbstractCard c = p.hand.getTopCard();
                this.addToBot(new ExhaustSpecificCardAction(c, p.hand));
                this.addToBot(new MakeTempCardInHandAction(new Circuit(), amount, true));
                this.returnCards();
                this.isDone = true;
                return;
            }
            */
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                if (c.type == AbstractCard.CardType.ATTACK) {
                    this.addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.handCardSelectScreen.selectedCards));
                    this.addToBot(new MakeTempCardInHandAction(new Circuit(), amount, true));
                }
            }
            this.returnCards();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }
    }

    private void returnCards() {
        for (AbstractCard c : this.cannotDuplicate) {
            this.p.hand.addToTop(c);
        }
        this.p.hand.refreshHandLayout();
    }
}
