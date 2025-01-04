import { ThemeProvider } from 'next-themes'
import { type ReactNode, type FC } from 'react'

type Props = {
  children: ReactNode
}

export const IcecreamThemeProvider: FC<Props> = ({ children }) => (
  <ThemeProvider attribute="class" defaultTheme="light" enableSystem disableTransitionOnChange>
    {children}
  </ThemeProvider>
)
