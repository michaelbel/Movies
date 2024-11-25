package org.michaelbel.movies.settings.iconAlias.impl

import org.michaelbel.movies.settings.iconAlias.IconAliasManager
import org.michaelbel.movies.ui.appicon.IconAlias

class IconAliasManagerImpl: IconAliasManager {

    override val enabledIcon: IconAlias
        get() = IconAlias.Red

    override fun setIcon(iconAlias: IconAlias) {}
}