package me.asleepp.skript_itemsadder.elements;

import ch.njol.skript.Skript;
import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import dev.lone.itemsadder.api.CustomStack;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

@Name("ItemsAdder Item")
@Description({"Gets an ItemsAdder item."})
@Examples({"give player itemsadder item icon_arrow_chest"})
public class ExprGetCustomItem extends SimpleExpression<ItemType> {

    private Expression<String> itemName;

    static {
        Skript.registerExpression(ExprGetCustomItem.class, ItemType.class, ExpressionType.SIMPLE, "(custom|ia|itemsadder) item %string%");
    }

    @Override
    protected ItemType[] get(Event e) {
        String name = itemName.getSingle(e);
        if (name != null) {
            CustomStack customStack = CustomStack.getInstance(name);
            if (customStack != null) {
                ItemStack item = customStack.getItemStack();
                return new ItemType[]{new ItemType(item)};
            }
        }
        return new ItemType[0];
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends ItemType> getReturnType() {
        return ItemType.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "ItemsAdder item " + itemName.toString(e, debug);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        itemName = (Expression<String>) exprs[0];
        return true;
    }
}