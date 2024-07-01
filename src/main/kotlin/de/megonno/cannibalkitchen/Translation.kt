package de.megonno.cannibalkitchen

import net.kyori.adventure.key.Key
import net.kyori.adventure.translation.GlobalTranslator
import net.kyori.adventure.translation.TranslationRegistry
import net.kyori.adventure.util.UTF8ResourceBundleControl
import java.util.Locale
import java.util.ResourceBundle

class Translation {
    val registry = TranslationRegistry.create(Key.key("cannibal_kitchen:translations"))

    val enBundle = ResourceBundle.getBundle("translations.Bundle", Locale.US, UTF8ResourceBundleControl.get())
    val deBundle = ResourceBundle.getBundle("translations.Bundle", Locale.GERMANY, UTF8ResourceBundleControl.get())

    fun register() {
        registry.registerAll(Locale.US, enBundle, true)
        registry.registerAll(Locale.GERMANY, deBundle, true)

        GlobalTranslator.translator().addSource(registry)
    }
}
