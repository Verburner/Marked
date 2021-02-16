package theMarked.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.EventRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import theMarked.characters.TheMarked;

public class EventRoomStartPatch {
    //Determine and overwrite strings on room creation
    @SpirePatch(clz = EventRoom.class, method = "onPlayerEntry")
    public static class ResetSprite {
        @SpirePostfixPatch
        public static void patch(EventRoom __instance) {
            if (AbstractDungeon.player instanceof TheMarked)
            {
                ((TheMarked)AbstractDungeon.player).resetAnimation();
            }
        }
    }
}
