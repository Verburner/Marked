package theMarked.cards;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.ImpactSparkEffect;
import theMarked.DefaultMod;
import theMarked.VFX.SwordBuffEffect;
import theMarked.actions.SummonAction;
import theMarked.actions.SwordPreserveAction;
import theMarked.characters.TheMarked;
import theMarked.powers.ChargePower;

import static theMarked.DefaultMod.makeCardPath;

public class OneWithTheBlade extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(OneWithTheBlade.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill_ElementalWeapon.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheMarked.Enums.MARKED_GENTA;

    private static final int COST = 1;
    private static final int MAGIC = 2;
    private static final int UPGRADE_PLUS_MAGIC = 4;


    // /STAT DECLARATION/


    public OneWithTheBlade() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.cardsToPreview = new AlyssasBlade();
        this.baseMagicNumber = MAGIC;
        this.magicNumber = baseMagicNumber;
        this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard sword = this.cardsToPreview;
        AbstractDungeon.actionManager.addToBottom(new SummonAction(sword));
        AbstractDungeon.actionManager.addToBottom(new SwordPreserveAction(1));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new ChargePower(p,p,magicNumber),magicNumber));

        this.addToBot(new VFXAction(new SwordBuffEffect(p.hb.cX, p.hb.cY, CardHelper.getColor(255.0F,255.0F,180.0F))));
        AbstractDungeon.actionManager.addToBottom(new SFXAction("HEART_BEAT"));
        for(int i = 0; i < 15; ++i) {
            AbstractDungeon.topLevelEffectsQueue.add(new ImpactSparkEffect(p.drawX + MathUtils.random(-20.0F, 20.0F) * Settings.scale + 150.0F * Settings.scale, p.drawY+ MathUtils.random(-20.0F, 20.0F) * Settings.scale));
        }
    }

    @Override
    public void hover()
    {
        if (AbstractDungeon.getCurrMapNode() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            this.cardsToPreview = this.searchBlade();
        } else this.cardsToPreview = new AlyssasBlade();
        super.hover();
    }

    private AbstractCard searchBlade()
    {
        AbstractCard x = new AlyssasBlade();
        AbstractCard ca = null;
        for (AbstractCard c : AbstractDungeon.player.hand.group)
        {
            if (c instanceof AlyssasBlade) ca = c;
        }
        for (AbstractCard c : AbstractDungeon.player.discardPile.group)
        {
            if (c instanceof AlyssasBlade) ca = c;
        }
        for (AbstractCard c : AbstractDungeon.player.drawPile.group)
        {
            if (c instanceof AlyssasBlade) ca = c;
        }
        if (ca != null)
        {
            x.baseDamage = ca.baseDamage;
            x.baseMagicNumber = ca.baseMagicNumber;
            ((AbstractDynamicCard)x).defaultBaseSecondMagicNumber = ((AbstractDynamicCard)ca).defaultBaseSecondMagicNumber;
            x.baseBlock = ca.baseBlock;

            x.damage = x.baseDamage;
            x.magicNumber = x.baseMagicNumber;
            ((AbstractDynamicCard)x).defaultSecondMagicNumber = ((AbstractDynamicCard)x).defaultBaseSecondMagicNumber;
            x.block = x.baseBlock;
            x.initializeDescription();
            return x;
        }
        return new AlyssasBlade();
    }
    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            initializeDescription();
        }
    }
}
