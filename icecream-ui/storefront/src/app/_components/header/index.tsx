import { requestAllCategories } from '@/repositories/frontier/categories'
import { CategoryDrawer } from './category-drawer'
import Link from 'next/link'
import Image from 'next/image'
import { SITE_NAME } from '@/lib/constants'
import { Search } from './search'
import { Menu } from './menu'

export const Header = async () => {
  const categories = await requestAllCategories()
  return (
    <header className="w-full border-b">
      <div className="wrapper flex-between">
        <div className="flex-start">
          <CategoryDrawer data={categories} />
          <Link href="/" className="flex-start ml-4">
            <Image src="/img/logo.svg" alt="App logo" height={48} width={48} priority />
            <span className="hidden lg:block font-bold text-2xl ml-3">{SITE_NAME}</span>
          </Link>
        </div>
        <div className="hidden md:block">
          <Search data={categories} />
        </div>
        <Menu />
      </div>
    </header>
  )
}
