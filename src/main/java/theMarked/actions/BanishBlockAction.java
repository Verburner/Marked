package theMarked.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theMarked.VFX.BanishEffect;

public class BanishBlockAction extends AbstractGameAction{

    public BanishBlockAction(AbstractCreature target, AbstractCreature source) {
        this.target = target;
        this.source = source;
    }
    public void update() {
            if (source.hasPower("theMarked:BanishPower"))
            {
                this.addToTop(new GainBlockAction(this.target, this.source.getPower("theMarked:BanishPower").amount));
            }
            this.isDone = true;
        }
}
