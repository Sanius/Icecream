import Link from 'next/link'
import { EllipsisVertical, ShoppingCart, UserIcon } from 'lucide-react'

import { Button } from '@/components/ui/button'
import {
  Sheet,
  SheetContent,
  SheetDescription,
  SheetTitle,
  SheetTrigger,
} from '@/components/ui/sheet'
import { ModeToggle } from '@/app/_components/mode-toggle'
import { auth, requestSSOSignOut, signIn, signOut } from '@/repositories/identity'
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuTrigger,
} from '@/components/ui/dropdown-menu'
import { isLoggedIn } from '@/lib/utils'

export const Menu = () => (
  <div className="flex justify-end gap-3">
    <nav className="hidden md:flex w-full max-w-xs gap-1">
      <ModeToggle />
      <Button asChild variant="ghost">
        <Link href="/cart">
          <ShoppingCart /> Cart
        </Link>
      </Button>
      <UserButton />
    </nav>
    <nav className="md:hidden">
      <Sheet>
        <SheetTrigger className="align-middle">
          <EllipsisVertical />
        </SheetTrigger>
        <SheetContent className="flex flex-col items-start">
          <SheetTitle>Menu</SheetTitle>
          <ModeToggle />
          <Button asChild variant="ghost">
            <Link href="/cart">
              <ShoppingCart /> Cart
            </Link>
          </Button>
          <UserButton />
          <SheetDescription></SheetDescription>
        </SheetContent>
      </Sheet>
    </nav>
  </div>
)

const UserButton = async () => {
  const session = await auth()
  if (!isLoggedIn(session)) {
    return (
      <form
        action={async () => {
          'use server'
          await signIn('keycloak')
        }}
      >
        <Button variant="ghost" type="submit">
          <UserIcon /> Sign In
        </Button>
      </form>
    )
  }
  const { firstName, lastName, refreshToken } = session
  return (
    <div className="flex gap-2 items-center">
      <DropdownMenu>
        <DropdownMenuTrigger asChild>
          <div className="flex items-center">
            <Button
              variant="ghost"
              className="relativee w-8 h-8 rounded-full ml-2 flex items-center justify-center bg-gray-200"
            >
              U
            </Button>
          </div>
        </DropdownMenuTrigger>
        <DropdownMenuContent className="w-56" align="end" forceMount>
          <DropdownMenuLabel className="font-normal">
            <div className="text-sm font-medium leading-none">{firstName} {lastName}</div>
          </DropdownMenuLabel>
          <DropdownMenuItem>
            <Link href="/user/profile" className="w-full">
              User Profile
            </Link>
          </DropdownMenuItem>
          <DropdownMenuItem>
            <Link href="/user/orders" className="w-full">
              Order History
            </Link>
          </DropdownMenuItem>
          <DropdownMenuItem className="p-0 mb-1">
            <form
              className="w-full"
              action={async () => {
                'use server'
                await Promise.all([signOut(), requestSSOSignOut(refreshToken)])
              }}
            >
              <Button className="w-full py-4 px-2 h-4 justify-start" variant="ghost" type="submit">
                Sign Out
              </Button>
            </form>
          </DropdownMenuItem>
        </DropdownMenuContent>
      </DropdownMenu>
    </div>
  )
}
