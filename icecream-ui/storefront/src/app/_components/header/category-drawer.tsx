import Link from 'next/link'
import { type FC } from 'react'
import { MenuIcon } from 'lucide-react'

import { Button } from '@/components/ui/button'
import type { Category } from '@/types'
import {
  Drawer,
  DrawerClose,
  DrawerContent,
  DrawerHeader,
  DrawerTitle,
  DrawerTrigger,
} from '@/components/ui/drawer'

type Props = {
  data: Category[]
}

export const CategoryDrawer: FC<Props> = async ({ data }) => (
  <Drawer direction="left">
    <DrawerTrigger asChild>
      <Button variant="outline">
        <MenuIcon />
      </Button>
    </DrawerTrigger>
    <DrawerContent className="h-full max-w-sm">
      <DrawerHeader>
        <DrawerTitle> Select a category</DrawerTitle>
        <div className="space-y-1 mt-4">
          {data.map((item) => (
            <Button variant="ghost" className="w-full justify-start" key={item.id} asChild>
              <DrawerClose asChild>
                <Link href={`/search?category=${item.slug}`}>{item.name}</Link>
              </DrawerClose>
            </Button>
          ))}
        </div>
      </DrawerHeader>
    </DrawerContent>
  </Drawer>
)
