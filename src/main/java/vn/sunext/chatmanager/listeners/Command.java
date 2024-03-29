package vn.sunext.chatmanager.listeners;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import vn.sunext.chatmanager.ChatManager;

public class Command implements Listener {

	@EventHandler
	public void blacklistCommand(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();
		String m = e.getMessage();
		List<String> mm = ChatManager.getInstance().getConfig().getStringList("Commands");
		if (p.hasPermission("rbantichat.admin") || p.isOp())
			return;
		for (String mmm : mm) {
			if (m.toLowerCase().equals("/" + mmm) || m.toLowerCase().startsWith("/" + mmm + " ")) {
				e.setCancelled(true);
				EventsManager.onParticles2(p);
				p.sendMessage(("&8[&cRBAntiChat&8] &aBạn không được dùng lệnh này!").replace("&", "§"));
				p.sendTitle("§c§o(( Đây là lệnh cấm ))", "", 10, 10, 10);
				p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§c§o(( Đây là lệnh cấm ))"));
				new BukkitRunnable() {
					public void run() {
						p.addPotionEffect((new PotionEffect(PotionEffectType.BLINDNESS, 60, 10)));
						p.addPotionEffect((new PotionEffect(PotionEffectType.SLOW, 50, 10)));
					}
				}.runTaskLater(ChatManager.getInstance(), 1L);
				String name = e.getPlayer().getDisplayName();
				String chat = e.getMessage();
				for (Player c : Bukkit.getOnlinePlayers()) {
					if (c.hasPermission("rbantichat.admin")) {
						c.sendMessage(("&8[&cRBAntiChat&8] &e" + name + " &avừa dùng lệnh cấm &f[&e&o" + chat + "&f]")
								.replace("&", "§"));
					}
				}
				return;
			}
		}
	}

}
