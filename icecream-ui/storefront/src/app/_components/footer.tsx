import { SITE_NAME } from '@/lib/constants'

export const Footer = () => {
  const currentYear = new Date().getFullYear()
  return (
    <footer className="border-t">
      <div className="p-5 flex-center">
        {currentYear} {SITE_NAME}. All rights Reserved
      </div>
    </footer>
  )
}
