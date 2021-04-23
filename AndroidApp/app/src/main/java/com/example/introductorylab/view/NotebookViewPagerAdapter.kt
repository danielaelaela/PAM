package com.example.introductorylab.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class NotebookViewPagerAdapter(supportFragmentManager: FragmentManager)
    : FragmentPagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val fragments = ArrayList<FragmentEntry>()

    override fun getCount(): Int = fragments.size

    override fun getPageTitle(position: Int): CharSequence = fragments[position].title

    override fun getItem(position: Int): Fragment = fragments[position].fragment

    fun registerFragment(title: String, fragment: Fragment) {
        fragments.add(FragmentEntry(fragment, title))
    }

    data class FragmentEntry(val fragment: Fragment, val title: String)
}