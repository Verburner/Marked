package theMarked.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.neow.NeowEvent;
import com.megacrit.cardcrawl.neow.NeowRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import theMarked.characters.TheMarked;

public class NeowRoomStartPatch {
    //Determine and overwrite strings on room creation
    @SpirePatch(clz = NeowEvent.class, method = "talk")
    public static class ResetSprite {
        @SpirePostfixPatch
        public static void patch(NeowEvent __instance) {
            if (AbstractDungeon.player instanceof TheMarked)
            {
                ((TheMarked)AbstractDungeon.player).startAnimation();
            }
        }
    }
}
