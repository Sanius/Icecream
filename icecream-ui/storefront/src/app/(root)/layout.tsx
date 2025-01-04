import { type ReactNode, type FC } from 'react'

import { Header } from '@/app/_components/header'
import { Footer } from '@/app/_components/footer'

type Props = {
  children: ReactNode
}

const Layout: FC<Props> = ({ children }) => (
  <div className="flex h-screen flex-col">
    <Header />
    <main className="flex-1 wrapper">{children}</main>
    <Footer />
  </div>
)

export default Layout
