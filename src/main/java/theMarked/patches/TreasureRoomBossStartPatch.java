package theMarked.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.TreasureRoom;
import com.megacrit.cardcrawl.rooms.TreasureRoomBoss;
import theMarked.characters.TheMarked;

public class TreasureRoomBossStartPatch {
    //Determine and overwrite strings on room creation
    @SpirePatch(clz = TreasureRoomBoss.class, method = "onPlayerEntry")
    public static class ResetSprite {
        @SpirePostfixPatch
        public static void patch(TreasureRoomBoss __instance) {
            if (AbstractDungeon.player instanceof TheMarked)
            {
                ((TheMarked)AbstractDungeon.player).resetAnimation();
            }
        }
    }
}
