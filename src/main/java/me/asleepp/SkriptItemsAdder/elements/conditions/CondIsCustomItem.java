package me.asleepp.SkriptItemsAdder.elements.conditions;

import ch.njol.skript.Skript;
import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import dev.lone.itemsadder.api.CustomStack;

import javax.annotation.Nullable;

@Name("Is ItemsAdder Item")
@Description({"Checks if the item is an ItemsAdder item."})
@Examples({"if player's tool is a custom item", "if player's tool is a custom item \"icon_arrow_chest\""})
public class CondIsCustomItem extends Condition {

    private Expression<ItemType> item;
    private Expression<String> itemId;

    static{
        Skript.registerCondition(CondIsCustomItem.class, new String[] {"%itemtypes% (is [a[n]]|are) (custom|ia|itemsadder) item[s] [[with id] %-string%]]"});
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        item = (Expression<ItemType>) exprs[0];
        itemId = (Expression<String>) exprs[1];
        return true;
    }

    @Override
    public boolean check(Event e) {
        ItemType[] items = item.getArray(e);
        if (items == null) {
            return false;
        }

        for (ItemType itemType : items) {
            ItemStack itemStack = itemType.getRandom();
            if (itemStack == null) {
                return false;
            }
            CustomStack customStack = CustomStack.byItemStack(itemStack);
            if (customStack == null) {
                return false;
            }
            if (itemId != null) {
                String id = itemId.getSingle(e);
                if (id == null || !customStack.getId().equals(id)) {
                    return false;
                }
            }
        }
        return true;
    }


    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return item.toString(e, debug) + " is a custom item" + (itemId != null ? " with id " + itemId.toString(e, debug) : "");
    }

}
