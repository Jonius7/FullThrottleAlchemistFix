# FullThrottle Alchemist Fix

[FullThrottle Alchemist](https://www.curseforge.com/minecraft/mc-mods/fullthrottle-alchemist) is an old but a great mod for 1.7.10, but it doesn't play nicely when loaded with multiple mods and causes a `ModSortingException` when Forge tries to sort out which mods load first. 

**FullThrottle Alchemist Fix** fixes this by removing the outdated Forestry API that it ships with. As FTA is All Rights Reserved, I cannot legally redistribute the patched `.jar` file and so this Fix mod exists. Drop it in the same folder as the latest FTA jar: `FullThrottle_Alchemist-1.7.10-1.0.18a.jar`

Upon running Minecraft it will patch the jar file and rename it to `FullThrottle_Alchemist-1.7.10-1.0.18a-patched.jar` while also creating a backup.



This was the minimal setup of 14 mods that the FTA Fix was tested against.

<details><summary>Minimal Mods List </summary>
<ul>
<li>Additional Buildcraft Objects</li>
<li>Biomes O&#39; Plenty</li>
<li>BuildCraft</li>
<li>CodeChicken Core</li>
<li>CodeChickenLib Unofficial 1.7.10</li>
<li>Forestry</li>
<li>FullThrottle Alchemist</li>
<li>FullThrottle NEI</li>
<li>Immersive Engineering</li>
<li>Mantle</li>
<li>Natura</li>
<li>NotEnoughItems</li>
<li>Railcraft</li>
<li>Tinkers&#39; Construct</li>
</ul>
