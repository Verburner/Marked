package theMarked.characters;

import basemod.abstracts.CustomPlayer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.neow.NeowRoom;
import com.megacrit.cardcrawl.rooms.EventRoom;
import com.megacrit.cardcrawl.rooms.ShopRoom;
import com.megacrit.cardcrawl.rooms.TreasureRoom;
import com.megacrit.cardcrawl.rooms.TreasureRoomBoss;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theMarked.DefaultMod;
import theMarked.actions.ePlace;
import theMarked.cards.*;
import theMarked.relics.ArtifactOfAir;
import theMarked.util.BetterSpriterAnimation;

import java.util.ArrayList;
import java.util.List;

import static theMarked.DefaultMod.*;
import static theMarked.characters.TheMarked.Enums.MARKED_GENTA;

//Wiki-page https://github.com/daviscook477/BaseMod/wiki/Custom-Characters
//and https://github.com/daviscook477/BaseMod/wiki/Migrating-to-5.0
//All text (starting description and loadout, anything labeled TEXT[]) can be found in DefaultMod-character-Strings.json in the resources

public class TheMarked extends CustomPlayer {
    public static final Logger logger = LogManager.getLogger(DefaultMod.class.getName());

    // =============== CHARACTER ENUMERATORS =================
    // These are enums for your Characters color (both general color and for the card library) as well as
    // an enum for the name of the player class - IRONCLAD, THE_SILENT, DEFECT, YOUR_CLASS ...
    // These are all necessary for creating a character. If you want to find out where and how exactly they are used
    // in the basegame (for fun and education) Ctrl+click on the PlayerClass, CardColor and/or LibraryType below and go down the
    // Ctrl+click rabbit hole

    public static class Enums {
        @SpireEnum
        public static AbstractPlayer.PlayerClass THE_DEFAULT;
        @SpireEnum(name = "MARKED_PURPLE_COLOR") // These two HAVE to have the same absolutely identical name.
        public static AbstractCard.CardColor MARKED_GENTA;
        @SpireEnum(name = "MARKED_PURPLE_COLOR") @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
    }

    // =============== CHARACTER ENUMERATORS  =================


    // =============== BASE STATS =================

    public static final int ENERGY_PER_TURN = 3;
    public static final int STARTING_HP = 72;
    public static final int MAX_HP = 72;
    public static final int STARTING_GOLD = 99;
    public static final int CARD_DRAW = 5;
    public static final int ORB_SLOTS = 0;

    // =============== /BASE STATS/ =================


    // =============== STRINGS =================

    private static final String ID = makeID("DefaultCharacter");
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    private static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;

    // =============== /STRINGS/ =================


    // =============== TEXTURES OF BIG ENERGY ORB ===============

    public static final String[] orbTextures = {
            "theMarkedResources/images/char/defaultCharacter/orb/layer1.png",
            "theMarkedResources/images/char/defaultCharacter/orb/layer2.png",
            "theMarkedResources/images/char/defaultCharacter/orb/layer3.png",
            "theMarkedResources/images/char/defaultCharacter/orb/layer4.png",
            "theMarkedResources/images/char/defaultCharacter/orb/layer5.png",
            "theMarkedResources/images/char/defaultCharacter/orb/layer6.png",
            "theMarkedResources/images/char/defaultCharacter/orb/layer1d.png",
            "theMarkedResources/images/char/defaultCharacter/orb/layer2d.png",
            "theMarkedResources/images/char/defaultCharacter/orb/layer3d.png",
            "theMarkedResources/images/char/defaultCharacter/orb/layer4d.png",
            "theMarkedResources/images/char/defaultCharacter/orb/layer5d.png",};

    // =============== /TEXTURES OF BIG ENERGY ORB/ ===============

    // =============== CHARACTER CLASS START =================

    public TheMarked(String name, PlayerClass setClass) {
        super(name, setClass, orbTextures,
                "theMarkedResources/images/char/defaultCharacter/orb/vfx.png", null,
                new BetterSpriterAnimation(
                        "theMarkedResources/images/char/defaultCharacter/Idle/idleSprite.scml"));
                ((BetterSpriterAnimation)this.animation).myPlayer.addListener(new Listener(this));
                ((BetterSpriterAnimation)this.animation).myPlayer.setAnimation("idle_noSword");


        // =============== TEXTURES, ENERGY, LOADOUT =================  

        initializeClass(null, // required call to load textures and setup energy/loadout.
                // I left these in DefaultMod.java (Ctrl+click them to see where they are, Ctrl+hover to see what they read.)
                THE_DEFAULT_SHOULDER_2, // campfire pose
                THE_DEFAULT_SHOULDER_1, // another campfire pose
                THE_DEFAULT_CORPSE, // dead corpse
                getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN)); // energy manager

        // =============== /TEXTURES, ENERGY, LOADOUT/ =================



        // =============== /ANIMATIONS/ =================


        // =============== TEXT BUBBLE LOCATION =================

        dialogX = (drawX + 0.0F * Settings.scale); // set location for text bubbles
        dialogY = (drawY + 220.0F * Settings.scale); // you can just copy these values

        // =============== /TEXT BUBBLE LOCATION/ =================

    }

    // =============== /CHARACTER CLASS END/ =================

    // Starting description and loadout
    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                STARTING_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, CARD_DRAW, this, getStartingRelics(),
                getStartingDeck(), false);
    }

    // Starting Deck
    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();

        logger.info("Begin loading starter Deck Strings");

        for (int i=0;i<5;i++) retVal.add(Strike.ID);
        for (int i=0;i<4;i++) retVal.add(Defend.ID);
        retVal.add(SoldierOfQuellstein.ID);
        return retVal;
    }

    // Starting Relics	
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();

        retVal.add(ArtifactOfAir.ID);
        UnlockTracker.markRelicAsSeen(ArtifactOfAir.ID);
        return retVal;
    }


    @Override
    public void damage(DamageInfo info) {
        int thisHealth = this.currentHealth;
        super.damage(info);
        int trueDamage = thisHealth - this.currentHealth;
        if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && trueDamage > 0) {
            if (this.isDead) { this.playDeathAnimation(); }
            else {
                if (this.hasSword()) ((BetterSpriterAnimation)this.animation).myPlayer.setAnimation("hurt");
                else ((BetterSpriterAnimation)this.animation).myPlayer.setAnimation("hurt_noSword");
            }
        }
        else if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && trueDamage == 0)
        {
            if (this.hasSword()) ((BetterSpriterAnimation)this.animation).myPlayer.setAnimation("block");
            else ((BetterSpriterAnimation)this.animation).myPlayer.setAnimation("block_noSword");
        }
    }


    public void swordBuff() {
        if (this.hasSword()) ((BetterSpriterAnimation)this.animation).myPlayer.setAnimation("buff");
        else ((BetterSpriterAnimation)this.animation).myPlayer.setAnimation("buff_noSword");
    }

    public void castAnimation() {
        if (this.hasSword()) ((BetterSpriterAnimation)this.animation).myPlayer.setAnimation("cast");
        else ((BetterSpriterAnimation)this.animation).myPlayer.setAnimation("cast_noSword");
    }
    public void loseSword() {
        ((BetterSpriterAnimation)this.animation).myPlayer.setAnimation("idle");
    }

    public boolean hasSword() {
        for (AbstractCard c : AbstractDungeon.player.hand.group)
        {
            if (c instanceof AlyssasBlade) return true;
        }
        for (AbstractCard c : AbstractDungeon.player.discardPile.group)
        {
            if (c instanceof AlyssasBlade) return true;
        }
        for (AbstractCard c : AbstractDungeon.player.drawPile.group)
        {
            if (c instanceof AlyssasBlade) return true;
        }
        for (AbstractCard c : AbstractDungeon.player.limbo.group) {
            if (c instanceof AlyssasBlade) return true;
        }
        for (CardQueueItem cq : AbstractDungeon.actionManager.cardQueue) {
            if (cq.card instanceof AlyssasBlade) return true;
        }
        if (AbstractDungeon.player.cardInUse instanceof AlyssasBlade)
        {
            return true;
        }
        return false;
    }
    // character Select screen effect
    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("STANCE_LOOP_CALM", 1.25f); // Sound Effect
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT,
                false); // Screen Effect
    }

    @Override
    public Texture getCutsceneBg()
    {
        return ImageMaster.loadImage("theMarkedResources/images/char/defaultCharacter/endingScreen/bgEndingScreen.jpg");
    }

    @Override
    public List<CutscenePanel> getCutscenePanels()
    {
        List<CutscenePanel> panels = new ArrayList<>();
        panels.add(new CutscenePanel("theMarkedResources/images/char/defaultCharacter/endingScreen/marked1.png", "ATTACK_HEAVY"));
        panels.add(new CutscenePanel("theMarkedResources/images/char/defaultCharacter/endingScreen/marked2.png"));
        panels.add(new CutscenePanel("theMarkedResources/images/char/defaultCharacter/endingScreen/marked3.png"));
        return panels;
    }

    // character Select on-button-press sound effect
    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "STANCE_LOOP_CALM";
    }

    // Should return how much HP your maximum HP reduces by when starting a run at
    // Ascension 14 or higher. (ironclad loses 5, defect and silent lose 4 hp respectively)
    @Override
    public int getAscensionMaxHPLoss() {
        return 4;
    }

    // Should return the card color enum to be associated with your character.
    @Override
    public AbstractCard.CardColor getCardColor() {
        return MARKED_GENTA;
    }

    // Should return a color object to be used to color the trail of moving cards
    @Override
    public Color getCardTrailColor() {
        return theMarked.DefaultMod.MARKED_PURPLE;
    }

    // Should return a BitmapFont object that you can use to customize how your
    // energy is displayed from within the energy orb.
    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    // Should return class name as it appears in run history screen.
    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }

    //Which card should be obtainable from the Match and Keep event?
    @Override
    public AbstractCard getStartCardForEvent() {
        return new SoldierOfQuellstein();
    }

    // The class name as it appears next to your player name in-game
    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return NAMES[1];
    }

    // Should return a new instance of your character, sending name as its name parameter.
    @Override
    public AbstractPlayer newInstance() {
        return new TheMarked(name, chosenClass);
    }

    // Should return a Color object to be used to color the miniature card images in run history.
    @Override
    public Color getCardRenderColor() {
        return theMarked.DefaultMod.MARKED_PURPLE;
    }

    // Should return a Color object to be used as screen tint effect when your
    // character attacks the heart.
    @Override
    public Color getSlashAttackColor() {
        return theMarked.DefaultMod.MARKED_PURPLE;
    }

    public void resetAnimation() {
        if (AbstractDungeon.getCurrRoom() instanceof ShopRoom || AbstractDungeon.getCurrRoom() instanceof TreasureRoom || AbstractDungeon.getCurrRoom() instanceof NeowRoom || AbstractDungeon.getCurrRoom() instanceof TreasureRoomBoss)
        {
            ((BetterSpriterAnimation) this.animation).myPlayer.setAnimation("idle_outOfCombat");
        }
        else {
            if (this.hasSword()) {
                ((BetterSpriterAnimation) this.animation).myPlayer.setAnimation("idle");
            } else {
                ((BetterSpriterAnimation) this.animation).myPlayer.setAnimation("idle_noSword");
            }
        }
    }
    public void startAnimation() {
        ((BetterSpriterAnimation) this.animation).myPlayer.setAnimation("idle_outOfCombat");
    }

    /*
    public void stopAnimation() {
        int time = ((BetterSpriterAnimation)this.animation).myPlayer.getAnimation().length;
        ((BetterSpriterAnimation)this.animation).myPlayer.setTime(time);
    }
    */

    // Should return an AttackEffect array of any size greater than 0. These effects
    // will be played in sequence as your character's finishing combo on the heart.
    // Attack effects are the same as used in DamageAction and the like.
    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.SLASH_DIAGONAL,
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL,
                AbstractGameAction.AttackEffect.LIGHTNING};
    }

    // Should return a string containing what text is shown when your character is
    // about to attack the heart. For example, the defect is "NL You charge your
    // core to its maximum..."
    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }

    // The vampire events refer to the base game characters as "brother", "sister",
    // and "broken one" respectively.This method should return a String containing
    // the full text that will be displayed as the first screen of the vampires event.
    @Override
    public String getVampireText() {
        return TEXT[2];
    }

}
