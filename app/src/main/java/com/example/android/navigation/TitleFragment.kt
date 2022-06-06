/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

 package com.example.android.navigation


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.android.navigation.databinding.FragmentTitleBinding


class TitleFragment : Fragment() {

    private lateinit var binding: FragmentTitleBinding  //ViewDataBinidngを親に持つ

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        //メソッドのジェネリックだから指定しなくても良き？
        //inflateの引数：inflater→LayoutInflaterであり、フラグメント内のビューを膨らませるために使用できるLayoutInflaterオブジェクト
        binding = DataBindingUtil.inflate<FragmentTitleBinding>(inflater, R.layout.fragment_title, container ,false)

        binding.playButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_titleFragment_to_gameFragment)
        }

        setHasOptionsMenu(true)

//        バインディングに関連付けられたレイアウトファイルの一番外側のView。
        return binding.root
    }

//    Fragmentホストの標準オプションメニューの内容を初期化します。メニューには、メニューアイテムを配置する必要があります。このメソッドを呼び出すには
//    まず setHasOptionsMenu を呼び出す必要があります。詳しくはActivity.onCreateOptionsMenuを参照してください。
    //オプションメニューを追加、メニューリソースファイルをインフレートしています。
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    //メニューアイテムがタップされたときに適切なアクションを選択するためのメソッド
    //アクションとはメニューアイテムのIDと同じIDをもつフラグメントに遷移させる
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        onNavDestinationSelected→指定されたMenuItemに関連付けられているに移動してみます。
        return NavigationUI.onNavDestinationSelected(item!!, view!!.findNavController()) || super.onOptionsItemSelected(item)
    }

}
