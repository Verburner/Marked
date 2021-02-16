package theMarked.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.VerticalAuraEffect;
import theMarked.DefaultMod;
import theMarked.actions.CastAnimationAction;
import theMarked.characters.TheMarked;
import theMarked.powers.BanishPower;

import static theMarked.DefaultMod.makeCardPath;

public class Memento extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(Memento.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill_Memento.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheMarked.Enums.MARKED_GENTA;

    private static final int COST = 1;
    private static final int MAGIC_NUMBER = 3;
    private static final int UPGRADE_PLUS_MAGIC = 1;
    private static final int MAGIC2_NUMBER = 2;


    // /STAT DECLARATION/


    public Memento() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.baseMagicNumber = MAGIC_NUMBER;
        this.magicNumber = baseMagicNumber;
        this.defaultBaseSecondMagicNumber = MAGIC2_NUMBER;
        this.defaultSecondMagicNumber = defaultBaseSecondMagicNumber;
        this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new CastAnimationAction());
        this.addToBot(new VFXAction(p, new VerticalAuraEffect(Color.BLUE, m.hb.cX, m.hb.cY), 0.15F));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new BanishPower(m, p, this.magicNumber), this.magicNumber, AbstractGameAction.AttackEffect.NONE));
        this.addToBot(new DrawCardAction(defaultBaseSecondMagicNumber));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            upgradeDefaultSecondMagicNumber(UPGRADE_PLUS_MAGIC);
            initializeDescription();
        }
    }
}
