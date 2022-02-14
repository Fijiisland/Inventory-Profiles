package org.anti_ad.mc.ipnext.config

import org.anti_ad.mc.common.Log
import org.anti_ad.mc.common.TellPlayer
import org.anti_ad.mc.common.gui.widgets.ButtonWidget
import org.anti_ad.mc.common.gui.widgets.ConfigButtonClickHandler
import org.anti_ad.mc.common.gui.widgets.ConfigButtonInfo
import org.anti_ad.mc.common.integration.HintsManagerNG
import org.anti_ad.mc.common.vanilla.alias.glue.I18n
import org.anti_ad.mc.common.vanilla.glue.VanillaUtil
import org.anti_ad.mc.ipnext.debug.GenerateRuleListButtonInfoDelegate
import org.anti_ad.mc.ipnext.debug.GenerateTagVanillaTxtButtonInfoDelegate
import org.anti_ad.mc.ipnext.parser.ProfilesLoader
import org.anti_ad.mc.ipnext.parser.ReloadRuleFileButtonInfoDelegate
import java.net.URL
import java.nio.file.Path
import java.util.*
import kotlin.concurrent.schedule

private val configFolder = VanillaUtil.configDirectory("inventoryprofilesnext")

val profileFilePath: Path
    get() = ProfilesLoader.file


class ExportHints(val asInternal: Boolean): ConfigButtonInfo() {
    override val buttonText: String
        get() = "Export Hints Data ${if (asInternal) "for integration" else "as separate files"}"

    override fun onClick(widget: ButtonWidget) {
        if (asInternal) {
            TellPlayer.chat("Generating ModIntegrationExport.json")
            HintsManagerNG.saveAllAsIntegrated(Modpacks.DIFF_CALCULATOR_PRIORITY.value)
        } else {
            HintsManagerNG.saveAllAsSeparate(Modpacks.DIFF_CALCULATOR_PRIORITY.value)
        }
    }
}

open class DefaultDelegatedConfigButtonInfo: ConfigButtonInfo() {
    open var delegate: ConfigButtonClickHandler? = null
    override fun onClick(widget: ButtonWidget) {
        delegate?.onClick {}
    }
}

object ReloadRuleFileButtonInfo : ConfigButtonInfo() {
    var delegate: ConfigButtonClickHandler? = ReloadRuleFileButtonInfoDelegate
    override val buttonText: String
        get() = I18n.translate("inventoryprofiles.gui.config.button.reload_rule_files")

    override fun onClick(widget: ButtonWidget) {
        delegate?.onClick {
            widget.active = false
            widget.text = I18n.translate("inventoryprofiles.gui.config.button.reload_rule_files.reloaded")
            Timer().schedule(5000) { // reset after 5 sec
                widget.text = buttonText
                widget.active = true
            }
        }
    }
}

object GenerateTagVanillaTxtButtonInfo : DefaultDelegatedConfigButtonInfo() {

    override var delegate: ConfigButtonClickHandler? = GenerateTagVanillaTxtButtonInfoDelegate
    override val buttonText: String
        get() = "generate tags.vanilla.txt"
}

object GenerateRuleListButtonInfo : DefaultDelegatedConfigButtonInfo() {
    override var delegate: ConfigButtonClickHandler? = GenerateRuleListButtonInfoDelegate
    override val buttonText: String
        get() = "generate native_rules.txt"
}

object OpenConfigFolderButtonInfo : ConfigButtonInfo() {
    override val buttonText: String
        get() = I18n.translate("inventoryprofiles.gui.config.button.open_config_folder")

    override fun onClick(widget: ButtonWidget) {
        VanillaUtil.open(configFolder.toFile())
    }
}

object OpenProfilesHelpButtonInfo : ConfigButtonInfo() {
    override val buttonText: String
        get() = I18n.translate("inventoryprofiles.gui.config.profiles_help_button")

    override fun onClick(widget: ButtonWidget) {
        VanillaUtil.open(URL("https://inventory-profiles-next.github.io/profiles/"))
    }
}

object OpenProfilesConfigButtonInfo : ConfigButtonInfo() {
    override val buttonText: String
        get() = I18n.translate("inventoryprofiles.gui.config.profiles_config_button")

    override fun onClick(widget: ButtonWidget) {
        if (VanillaUtil.inGame()) {
            VanillaUtil.open(profileFilePath.toFile())
        }
    }
}