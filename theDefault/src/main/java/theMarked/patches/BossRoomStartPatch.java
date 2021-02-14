package theMarked.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import theMarked.characters.TheMarked;

public class BossRoomStartPatch {
    //Determine and overwrite strings on room creation
    @SpirePatch(clz = MonsterRoomBoss.class, method = "onPlayerEntry")
    public static class ResetSprite {
        @SpirePostfixPatch
        public static void patch(MonsterRoomBoss __instance) {
            if (AbstractDungeon.player instanceof TheMarked)
            {
                ((TheMarked)AbstractDungeon.player).resetAnimation();
            }
        }
    }
}
