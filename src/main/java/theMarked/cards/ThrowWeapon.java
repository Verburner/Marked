package theMarked.cards;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ThrowDaggerEffect;
import theMarked.DefaultMod;
import theMarked.actions.ThrowWeaponAction;
import theMarked.characters.TheMarked;

import java.util.Iterator;

import static theMarked.DefaultMod.makeCardPath;

public class ThrowWeapon extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Special Strike: Deal 9(+3) damage.
     */

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(ThrowWeapon.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack_ThrowWeapon.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheMarked.Enums.MARKED_GENTA;

    private static final int COST = -1;

    public int specialDamage;

    // /STAT DECLARATION/

    public ThrowWeapon() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = 0;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.upgraded)
            this.addToBot(new ThrowWeaponAction(p,m,this.energyOnUse+1,this, this.freeToPlayOnce));
        else
            this.addToBot(new ThrowWeaponAction(p,m,this.energyOnUse,this, this.freeToPlayOnce));
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        } else {
            Iterator var4 = p.hand.group.iterator();
            canUse = false;
            while(var4.hasNext()) {
                AbstractCard c = (AbstractCard)var4.next();
                if (c.type == CardType.ATTACK && c != this) {
                    canUse = true;
                }
            }
            if (!canUse) this.cantUseMessage = "I don't have an Attack to throw. ";
            return canUse;
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}