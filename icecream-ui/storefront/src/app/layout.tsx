import { type ReactNode, type FC } from 'react'
import { Inter } from 'next/font/google'

import { SITE_NAME } from '@/lib/constants'
import { AuthProvider } from '@/components/providers/auth-provider'
import { QueryProvider } from '@/components/providers/client-query-provider'
import { ToastMessageProvider } from '@/components/providers/toast-provider'
import { IcecreamThemeProvider } from '@/components/providers/theme-provider'

import '@/global.css'

const font = Inter({ subsets: ['latin'] })

export const metadata = {
  title: SITE_NAME,
  description: `Welcome to ${SITE_NAME}`,
}

type Props = {
  children: ReactNode
}

const RootLayout: FC<Props> = ({ children }) => (
  <html lang="en" suppressHydrationWarning>
    <head>
      <link rel="icon" href="/img/favicon.ico" sizes="any" />
    </head>
    <body className={`${font.className} antialiased`}>
      <AppProvider>{children}</AppProvider>
    </body>
  </html>
)

export default RootLayout

type AppProviderProps = {
  children: ReactNode
}

const AppProvider: FC<AppProviderProps> = async ({ children }) => (
  <IcecreamThemeProvider>
    <AuthProvider>
      <QueryProvider>
        <ToastMessageProvider>{children}</ToastMessageProvider>
      </QueryProvider>
    </AuthProvider>
  </IcecreamThemeProvider>
)
