package theMarked.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.unique.SkewerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ThrowDaggerEffect;

import java.util.ArrayList;
import java.util.Iterator;

public class ThrowWeaponAction extends AbstractGameAction{
    private AbstractCard card;
    private boolean freeToPlayOnce;

    public ThrowWeaponAction(AbstractPlayer p, AbstractMonster m, int amount, AbstractCard card, boolean freeToPlayOnce)
    {
        this.amount = amount;
        this.actionType = ActionType.DAMAGE;
        this.duration = 0.25F;
        this.p = p;
        this.m = m;
        this.card = card;
        this.freeToPlayOnce = freeToPlayOnce;
    }

    private AbstractPlayer p;
    private AbstractMonster m;
    private ArrayList<AbstractCard> cannotDuplicate = new ArrayList();




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
                        card.baseDamage = c.baseDamage;
                        card.calculateCardDamage(m);
                        this.addToBot(new SkewerAction(p, m, card.damage, card.damageTypeForTurn, this.freeToPlayOnce, this.amount));
                        this.addToBot(new ExhaustSpecificCardAction(c, p.hand));
                        this.addToBot(new VFXAction(new ThrowDaggerEffect(m.hb.cX, m.hb.cY)));
                        this.isDone = true;
                        return;
                    }
                }
            }

            this.p.hand.group.removeAll(this.cannotDuplicate);
            if (this.p.hand.group.size() > 1) {
                AbstractDungeon.handCardSelectScreen.open("exhaust.", 1, false, false, false, false);
                this.tickDuration();
                return;
            }

            if (this.p.hand.group.size() == 1) {
                AbstractCard c = p.hand.getTopCard();

                card.baseDamage = c.baseDamage;
                card.calculateCardDamage(m);
                this.addToBot(new SkewerAction(p, m, card.damage, card.damageTypeForTurn, this.freeToPlayOnce, this.amount));
                this.addToBot(new ExhaustSpecificCardAction(c, p.hand));
                this.addToBot(new VFXAction(new ThrowDaggerEffect(m.hb.cX, m.hb.cY)));
                this.returnCards();
                this.isDone = true;
                return;
            }
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                if (c.type == AbstractCard.CardType.ATTACK) {
                    card.baseDamage = c.baseDamage;
                    card.calculateCardDamage(m);
                    this.addToTop(new ExhaustSpecificCardAction(c,AbstractDungeon.handCardSelectScreen.selectedCards));
                    this.addToBot(new SkewerAction(p, m, card.damage, card.damageTypeForTurn, this.freeToPlayOnce, this.amount));
                    this.addToBot(new VFXAction(new ThrowDaggerEffect(m.hb.cX, m.hb.cY)));
                }
            }
            this.returnCards();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
            return;
        }

        this.tickDuration();
    }

    private void returnCards() {

        for (AbstractCard c : this.cannotDuplicate)
        {
            this.p.hand.addToTop(c);
        }
        this.p.hand.refreshHandLayout();
    }
}
