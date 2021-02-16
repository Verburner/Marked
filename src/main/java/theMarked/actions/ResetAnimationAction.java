package theMarked.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import theMarked.cards.AlyssasBlade;
import theMarked.characters.TheMarked;

public class ResetAnimationAction extends AbstractGameAction{
    private AbstractCard card;

    public ResetAnimationAction(){}

    public void update() {

        if (AbstractDungeon.player instanceof TheMarked) {
            ((TheMarked) AbstractDungeon.player).resetAnimation();
        }
        this.isDone = true;
    }
}